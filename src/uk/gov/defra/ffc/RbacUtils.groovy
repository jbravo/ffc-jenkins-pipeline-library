package uk.gov.defra.ffc

public class RbacUtils {
  private String getName(String role) {
    "$role-1"
  }

  public void createRoleBinding(role) {
    String name = getName(role)
    println "$name-ROLEBINDING"
  }
}
